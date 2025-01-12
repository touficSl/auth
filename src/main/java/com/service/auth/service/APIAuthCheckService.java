package com.service.auth.service;

public interface APIAuthCheckService {

	boolean authenticate(String apikey, String apisecret);

}
