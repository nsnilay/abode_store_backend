package com.merchanteg.merchant.controller;


import com.merchanteg.merchant.dto.MerchantUpdate;
import com.merchanteg.merchant.dto.ProductMerchant;
import com.merchanteg.merchant.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MerchantController {

    private int minScaleForOfferings = 0;
    private int maxScaleForOfferings = 10;
    private int minScaleForSoldOrders = 0;
    private int maxScaleForSoldOrders = 50;
    private int minScaleForCurrstock = 0;
    private int maxScaleForCurrStock = 100;
    private int minScaleForMerchantRating = 0;
    private int maxScaleForMerchantRating = 5;
    private int minScaleForProductRating = 0;
    private int maxScaleForProductRating = 5;
    private float weightMerchantrating = (float) 0.3;
    private float weightProductrating = (float) 0.3;
    private float weightPrice = (float) -0.1;
    private float weightOffering = (float) 0.1;
    private float weightOrders = (float) 0.1;
    private float weightcurrStock = (float) 0.1;

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(method = RequestMethod.GET, value = "/getMerchantByProductId/{productId}")
    public List<ProductMerchant> getByProductId(@PathVariable("productId") String productId){
        List<ProductMerchant> productMerchants = merchantService.getByProductId(productId);
        Collections.sort(productMerchants,new SortByScore());

        return productMerchants;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/updateMerchantById")
    public Boolean updateMerchantById(@RequestBody List<MerchantUpdate> merchantUpdate){



        try{
            update(merchantUpdate);
            return true;
        }
        catch (Exception exception){
            System.out.println(exception.toString());
            return false;
        }

    }

    @RequestMapping(value = "/updateMerchant/{id}")
    public ResponseEntity<String> getEmployeeById (@PathVariable("id") String id)
    {
        System.out.println(id);
        StringTokenizer stringTokenizer = new StringTokenizer(id,",");
        List<MerchantUpdate> merchantUpdateList = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
           String temp = stringTokenizer.nextToken();
           StringTokenizer tokenForUpdate = new StringTokenizer(temp,":");
           String[] tokens = new String[2];
           int index = 0;
           while(tokenForUpdate.hasMoreTokens())
           {
               tokens[index] = tokenForUpdate.nextToken();
               System.out.println(tokens[index]);
               index++;
           }
           ProductMerchant productMerchant = merchantService.getOneByProductMerchantId(tokens[0]);
           MerchantUpdate merchantUpdate = new MerchantUpdate();
           merchantUpdate.setItemsSold(Integer.parseInt(tokens[1]));
           merchantUpdate.setMerchantId(productMerchant.getMerchant().getMerchantId());
           merchantUpdate.setProductId(productMerchant.getProductId());
           merchantUpdateList.add(merchantUpdate);
            System.out.println(merchantUpdate.getMerchantId());
        }
        try {
            update(merchantUpdateList);
            return new ResponseEntity<String>("Updated", HttpStatus.OK);
        }
        catch (Exception exception)
        {
            exception.toString();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    void update(List<MerchantUpdate> merchantUpdates){
        String mId,pId;
        int itemsSold,currentStock, currordersSold,currOfferings;

        for( MerchantUpdate merchant : merchantUpdates) {
            mId = merchant.getMerchantId();
            pId = merchant.getProductId();
            itemsSold = merchant.getItemsSold();

            ProductMerchant productMerchantEntity = merchantService.getOneByProductMerchantId((mId+pId));

            currentStock = productMerchantEntity.getCurrentStock();
            productMerchantEntity.setCurrentStock(currentStock-itemsSold);

            currordersSold = productMerchantEntity.getNumberOfOrdersSold();
            productMerchantEntity.setNumberOfOrdersSold(currordersSold + itemsSold);

            currOfferings = productMerchantEntity.getNumberOfOfferings();
            if(currOfferings >= currentStock-itemsSold){
                productMerchantEntity.setNumberOfOfferings(currentStock - itemsSold);
            }
            float score = updateMerchantScore(productMerchantEntity);
            productMerchantEntity.getMerchant().setMerchantScore(score);
            // call the method to update the data

            merchantService.save(productMerchantEntity);



        }

    }

    float updateMerchantScore(ProductMerchant productMerchantEntity)
    {
        List<ProductMerchant> productMerchantEntities = merchantService.getByProductId(productMerchantEntity.getProductId());
        productMerchantEntities.sort(Comparator.comparing(ProductMerchant::getPrice));
        String minimum =  productMerchantEntities.get(0).getPrice();
        String maximum =  productMerchantEntities.get(productMerchantEntities.size()-1).getPrice();
        try{
            float normalizedPrice = 100*(weightPrice * normalize(Integer.parseInt(minimum),Integer.parseInt(maximum),Integer.parseInt(productMerchantEntity.getPrice())));
            float weightOffering = 100*(this.weightOffering * normalize(minScaleForOfferings, maxScaleForOfferings,productMerchantEntity.getNumberOfOfferings()));
            float weightCurrStock = 100*(weightcurrStock *normalize(minScaleForCurrstock, maxScaleForCurrStock,productMerchantEntity.getCurrentStock()));
            float weightordersSold = 100*(weightOrders * normalize(minScaleForSoldOrders, maxScaleForSoldOrders,productMerchantEntity.getNumberOfOrdersSold()));
            float weightMerchantRating =100*(weightMerchantrating * normalize(minScaleForMerchantRating, maxScaleForMerchantRating,productMerchantEntity.getMerchant().getMerchantRating()));
            float weightProductRatings = 100*(weightProductrating * normalize(minScaleForProductRating, maxScaleForProductRating,Integer.parseInt(productMerchantEntity.getProductRating())));
            float score = (normalizedPrice + weightOffering + weightCurrStock + weightordersSold + weightMerchantRating + weightProductRatings)/(weightOrders + weightPrice +
                    weightcurrStock + this.weightOffering + weightProductrating + weightMerchantrating);
            return score;
        }
        catch (ArithmeticException e)
        {
            System.out.println("Arthematic Exception caught ");
            return 0;

        }

    }

    float normalize(float min,float max,float curr){
        float num, den;
        float res;
        if(max-min == 0){
            den = 1;
        }else{
            den = max - min;
        }
        num = curr-min;
        res = num/den;
        return res;
    }

}

class SortByScore implements Comparator<ProductMerchant>
{
    @Override
    public int compare(ProductMerchant o1, ProductMerchant o2) {
        return (int)(o1.getMerchant().getMerchantScore()-o2.getMerchant().getMerchantScore());
    }
}
