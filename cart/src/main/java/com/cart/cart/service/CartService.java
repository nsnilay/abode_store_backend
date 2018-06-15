package com.cart.cart.service;

import com.cart.cart.dto.Cart;
import com.cart.cart.dto.History;
import com.cart.cart.entity.CartEntity;
import com.cart.cart.entity.HistoryEntity;

import java.util.List;

public interface CartService {

    List<History> getByEmailId(String emailId);
    List<Cart> getByEmailIdInCart(String emailId);
    boolean save(Cart cartEntity);
    Cart getOneByID(String id);
    boolean saveToHistory(HistoryEntity historyEntity);
    boolean delete(Cart cart);
    boolean existsById(String id);
}
