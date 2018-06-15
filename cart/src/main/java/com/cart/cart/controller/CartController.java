package com.cart.cart.controller;


import com.cart.cart.dto.Cart;
import com.cart.cart.dto.History;
import com.cart.cart.entity.HistoryEntity;
import com.cart.cart.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

@RestController
public class CartController {

    @Value("${gmail.username}")
    private String userName;
    @Value("${gmail.password}")
    private String password;
    @Value("${mail.smtp.auth}")
    private String smtpAuth;
    @Value("${mail.smtp.starttls.enable}")
    private String smtpEnable;
    @Value("${mail.smtp.host}")
    private String smtpHost;
    @Value("${mail.smtp.port}")
    private String smtpPort;
    @Value("${url.to.get.pName}")
    private String url;
    @Value("${url.to.update}")
    private String urlUpdate;

    // Send request with GET method and default Headers.

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private CartService cartService;

    //History method call
    @RequestMapping(method = RequestMethod.GET, value = "/cart/history/{emailId}")
    public List<History> getAllByEmailId (@PathVariable("emailId") String emailId){
        return cartService.getByEmailId(emailId);
    }

    //on the click of cart
    @RequestMapping(method = RequestMethod.GET, value = "/cart/{emailId}")
    public List<Cart> getAllByEmailIdInCart (@PathVariable("emailId") String emailId){
        return  cartService.getByEmailIdInCart(emailId);
    }

    //while adding products to cart
    @RequestMapping(method = RequestMethod.POST, value = "/cart/addToCart")
    public boolean addToCart(@RequestBody Cart cart)
    {
        String pId = cart.getProductId();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", pId);
        RestTemplate restTemplate = new RestTemplate();
        String nameAndUrl= restTemplate.getForObject(url, String.class, params);
        System.out.println(nameAndUrl);
        StringTokenizer st1 =
                new StringTokenizer(nameAndUrl,",");
        String[] tokens = new String[2];
        int index=0;
        while (st1.hasMoreTokens()){
            tokens[index] = st1.nextToken();
            index++;
        }
        cart.setpName(tokens[0]);
        cart.setpUrl(tokens[1]);
        Cart cartNew = new Cart();
        if(cartService.existsById(cart.getId())){
            cartNew = cartService.getOneByID(cart.getId());
            cartNew.setQuantity(cartNew.getQuantity()+cart.getQuantity());
            //return true;
        }
        else {
            BeanUtils.copyProperties(cart,cartNew);
        }
        return cartService.save(cartNew);
    }


    @RequestMapping("/test")
    public String get()
    {
        Map<String, String> params = new HashMap<>();
        params.put("id", "p1");
        RestTemplate restTemplate = new RestTemplate();
        String pName = restTemplate.getForObject(url, String.class, params);
        System.out.println(pName);
        return pName;
    }

    //on the click of buynow
//    @RequestMapping(method = RequestMethod.GET,value = "/cart/buynow/{emailId}")
//    public boolean buynow(@PathVariable("emailId") String emailId)
//    {
//        try {
//            List<Cart> carts = cartService.getByEmailIdInCart(emailId);
//            //StringBuilder stringBuilder = new StringBuilder();
//            int size = carts.size();
//            int index = 0;
//            for(Cart cart:carts) {
//                HistoryEntity historyEntity = new HistoryEntity();
//                BeanUtils.copyProperties(cart, historyEntity);
//                cartService.saveToHistory(historyEntity);
//                cartService.delete(cart);
////                stringBuilder.append(cart.getId());
////                stringBuilder.append(":");
////                stringBuilder.append(String.valueOf(cart.getQuantity()));
////                index++;
////                if(index != size-1) {
////                    stringBuilder.append(",");
////                    break;
////                }
//            }
////            System.out.println(stringBuilder.toString());
////            String stringUpdate = stringBuilder.toString();
////            Map<String, String> params = new HashMap<>();
////            params.put("id",stringUpdate);
////            RestTemplate restTemplate = new RestTemplate();
////            String nameAndUrl= restTemplate.getForObject(urlUpdate, String.class, params);
//            //sendEmail(carts);
//            return true;
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.toString());
//            return false;
//        }
//    }

    @RequestMapping(method = RequestMethod.GET,value = "/cart/buynow/{emailId}")
    public String buynow1(@PathVariable("emailId") String emailId)
    {
        try {
            List<Cart> carts = cartService.getByEmailIdInCart(emailId);
            StringBuilder stringBuilder = new StringBuilder();
            int size = carts.size();
            int index = 0;
            for(Cart cart:carts) {
                HistoryEntity historyEntity = new HistoryEntity();
                BeanUtils.copyProperties(cart, historyEntity);
                cartService.saveToHistory(historyEntity);
                cartService.delete(cart);
                stringBuilder.append(cart.getMerchantId()+cart.getProductId());
                stringBuilder.append(":");
                stringBuilder.append(String.valueOf(cart.getQuantity()));
                index++;
                if(index != size-2) {
                    stringBuilder.append(",");
                }
            }
            System.out.println(stringBuilder.toString());
            String stringUpdate = stringBuilder.toString();
            Map<String, String> params = new HashMap<>();
            params.put("id",stringUpdate);
            RestTemplate restTemplate = new RestTemplate();
            String nameAndUrl= restTemplate.getForObject(urlUpdate, String.class, params);
            //sendEmail(carts);
            return nameAndUrl;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return null;
        }
    }

    //on the click of delete in cart
    @RequestMapping(method = RequestMethod.GET,value = "cart/delete/{id}")
    public List<Cart> delete(@PathVariable("id") String id)
    {
        Cart cart = cartService.getOneByID(id);
        List<Cart> carts=new ArrayList<>(); 
        try{
            cartService.delete(cart);
            carts = cartService.getByEmailIdInCart(cart.getEmailId());
            return carts;
        }
        catch(Exception exception)
        {
            exception.toString();
            return null;
        }
    }

    private void sendEmail(List<Cart> carts) throws javax.mail.MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth",smtpAuth);
        properties.put("mail.smtp.starttls.enable",smtpEnable);
        properties.put("mail.smtp.host",smtpHost);
        properties.put("mail.smtp.port",smtpPort);

        javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName,password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userName,false));
        ((MimeMessage) message).setRecipients(Message.RecipientType.TO,InternetAddress.parse(carts.get(carts.size()-1).getEmailId()));
        message.setSubject("Welcome to this App");
        StringBuilder stringBuilder = new StringBuilder();
        for(Cart cart: carts) {
            stringBuilder.append(cart.toString());
        }
        //message1.setContent("textMessage","text/html");
        message.setContent("Thank you for purchasing" + stringBuilder,"text/html");
        message.setSentDate(new Date());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent("textMessage","text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        Transport.send(message);
    }
}
