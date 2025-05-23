package com.example.productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;

@Service

public class ProductoService {
    
    @Autowired

    private ProductoRepository productoRepository;

    public List <Producto> buscarProducto(){
        return productoRepository.findAll();
    }

    public Producto buscarProductoPorId(Long id){
        return productoRepository.findById(id).get();

    }

    public Producto agregarProducto(Producto producto){
        return productoRepository.save(producto);

    }

    public  void eliminarProducto(Long id){
        productoRepository.deleteById(id);
    }




}
