package com.se.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se.model.Merchant;
import com.se.repository.MerchantRepository;


@Service
public class MerchantService {

	@Autowired
	MerchantRepository dao;

//	@Override
	public List<Merchant> getMerchants() {
		return dao.findAll();
	}
//	@Override
	public Optional<Merchant> getMerchantById(Long merchantid) {
		return dao.findById(merchantid);
	}
//	@Override
	public Merchant addNewMerchant(Merchant merchant) {
		return dao.save(merchant);
	}
//	@Override
	public Merchant updateMerchant(Merchant merchant) {
		return dao.save(merchant);
	}
//	@Override
	public void deleteMerchantById(Long merchantid) {
		dao.deleteById(merchantid);
	}
//	@Override
	public void deleteAllMerchants() {
		dao.deleteAll();
	}
}