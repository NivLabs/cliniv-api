package br.com.nivlabs.cliniv.controller;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.nivlabs.cliniv.service.BaseService;

/**
 * Tratador de serviços para controllers
 *
 * @author viniciosarodrigues
 * @since 02-09-2021
 *
 * @param <T>
 */
public abstract class BaseController<T extends BaseService> {

    @Autowired
    protected T service;
}
