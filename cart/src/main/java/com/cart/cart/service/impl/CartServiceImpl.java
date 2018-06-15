package com.cart.cart.service.impl;

import com.cart.cart.dto.Cart;
import com.cart.cart.dto.History;
import com.cart.cart.entity.CartEntity;
import com.cart.cart.entity.HistoryEntity;
import com.cart.cart.repository.CartRepository;
import com.cart.cart.repository.HistoryRepository;
import com.cart.cart.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<History> getByEmailId(String emailId) {

        List<HistoryEntity> historyEntities = historyRepository.getByEmailId(emailId);
        List<History> histories = new ArrayList<>();

        for(HistoryEntity historyEntity : historyEntities){
            History history = new History();
            BeanUtils.copyProperties(historyEntity,history);
            histories.add(history);
        }
        return histories;
    }



    @Override
    public List<Cart> getByEmailIdInCart(String emailId) {
        List<CartEntity> cartEntities = cartRepository.getByEmailId(emailId);
        List<Cart> carts = new ArrayList<>();

        for(CartEntity cartEntity : cartEntities){
            Cart cart = new Cart();
            BeanUtils.copyProperties(cartEntity,cart);
            carts.add(cart);
        }
        return carts;
    }

    @Override
    public boolean save(Cart cart) {
        CartEntity cartEntity = new CartEntity();
        BeanUtils.copyProperties(cart,cartEntity);
        try {
            CartEntity cartEntity1 = cartRepository.save(cartEntity);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public Cart getOneByID(String id) {
        CartEntity cartEntity = new CartEntity();
        cartEntity = cartRepository.getOneById(id);
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartEntity,cart);
        return cart;
    }

    @Override
    public boolean saveToHistory(HistoryEntity historyEntity) {
        try {
            HistoryEntity historyEntity1 = historyRepository.save(historyEntity);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean delete(Cart cart) {
        CartEntity cartEntity = new CartEntity();
        BeanUtils.copyProperties(cart,cartEntity);
        try {
            cartRepository.delete(cartEntity);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean existsById(String id) {
        if(cartRepository.existsById(id)){return true;}
        else{ return false;}
    }


}
