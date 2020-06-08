package com.se.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.annotation.CurrentUser;
import com.se.exception.ResourceNotFoundException;
import com.se.model.CustomUserDetails;
import com.se.model.Merchant;
import com.se.model.Role;
import com.se.model.RoleName;
import com.se.model.User;
import com.se.repository.MerchantRepository;
import com.se.repository.RoleRepository;
import com.se.repository.UserRepository;
import com.se.model.payload.ApiResponse;
import com.se.service.MerchantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/merchants")
@Api(value = "Merchant Rest API", description = "Defines endpoints for the logged in user. It's secured by default")
public class MerchantController {

	@Autowired
	MerchantService service;
	
	@Autowired
	MerchantRepository merchantRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;

	@GetMapping(value= "/all")
	@ApiOperation(value = "gets list of all merchants")
	public List<Merchant> getMerchant() {
		System.out.println(this.getClass().getSimpleName() + " - Get all merchant service is invoked.");
		return service.getMerchants();
	}
	
	
	@GetMapping
	@ApiOperation(value = "Gets the merchant related to the logged in user")
	public ResponseEntity<?> getMerchant(@CurrentUser CustomUserDetails currentUser) {
		Long userId = currentUser.getId();
		Optional<User> byId = userRepository.findById(userId);  
		if (!byId.isPresent())
			 return new ResponseEntity<Object>(new ApiResponse(false, " please login or pass the authentication in order to get the details"),
	                    HttpStatus.BAD_REQUEST);
        User user = byId.get();
//		Optional<Merchant> merchantbyId =  service.getMerchantById(user.getMerchant().getId());
		Merchant merchant =user.getMerchant();
		if (user.getMerchant()==null)
			 return new ResponseEntity<Object>(new ApiResponse(false, "Could not find merchant related to with userid- " +userId),
	                    HttpStatus.BAD_REQUEST);			
		System.out.println(this.getClass().getSimpleName() + " - Get all merchant service is invoked.");
		 return ResponseEntity.ok().body(merchant);
	}
	

	@GetMapping(value= "/{id}")
	@ApiOperation(value = "Gets the merchant details based on ID")
	public Merchant getMerchantById(@PathVariable long id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Get merchant details by id is invoked.");

		Optional<Merchant> byId =  service.getMerchantById(id);
		Merchant merchant = byId.get();
		if(merchant.getIsActive()==false)
			throw new Exception("user account deactivated");
		if(!byId.isPresent())
			throw new Exception("Could not find merchant with id- " + id);
		return merchant;
	}

	@PostMapping
	@ApiOperation(value = "Creates Merchant under the logged in user")
	public ResponseEntity<?> createMerchant(@RequestBody @Valid Merchant merchant, @CurrentUser CustomUserDetails currentUser) {
		System.out.println(this.getClass().getSimpleName() + " - Create new merchant method is invoked.");
		Long userId = currentUser.getId();
		String companyname = merchant.getCompany_name();
		String gst = merchant.getGst_no();
		Optional<Merchant> byName = merchantRepository.findByCompanyName(companyname);
		Optional<Merchant> bygst = merchantRepository.findByGstNo(gst);
		Optional<Merchant> byUser = merchantRepository.findByUserIdAndIsActive(userId);
		
		
		
		if(byUser.isPresent())
		{
			return new ResponseEntity<Object>(new ApiResponse(false, "a merchant exists for this user account" +currentUser.getUsername()),
                    HttpStatus.BAD_REQUEST);
		}
		if(byName.isPresent())
		{
			return new ResponseEntity<Object>(new ApiResponse(false, "company with name" +companyname+" already exists"),
                    HttpStatus.BAD_REQUEST);
		}
		if(bygst.isPresent())
		{
			return new ResponseEntity<Object>(new ApiResponse(false, "company with gst" +gst+" already exists"),
                    HttpStatus.BAD_REQUEST);
		}
		
		Optional<Role> role1 = roleRepository.findByRole(RoleName.ROLE_MERCHANT);
		Optional<Role> role2 = roleRepository.findByRole(RoleName.ROLE_CUSTOMER);
		
		if(!currentUser.getRoles().contains(role1.get()) && currentUser.getRoles().contains(role2.get())) {
			
		}
		
		return userRepository.findById(userId ).map(user -> {
			merchant.setUser(user);
            service.addNewMerchant(merchant);
            return ResponseEntity.status(HttpStatus.CREATED).body(merchant);                
        }).orElseThrow(() -> new ResourceNotFoundException("unable to add merchant", merchant.getId().toString(), merchant.getId()));			
		
	}
	
	

	
	@PutMapping(value= "/deactivate/{id}") 
	@ApiOperation(value = "Deactivated the merchant based on ID")
	public Merchant deleteMerchantById(@PathVariable long id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete merchant by id is invoked.");

		Optional<Merchant> byId =  service.getMerchantById(id);
		Merchant merchant = byId.get();
		if(!byId.isPresent())
			throw new Exception("Could not find merchant with id- " + id);
		merchant.setIsActive(false);		
		return service.updateMerchant(merchant);
		//		service.deleteMerchantById(id);
	}
	
	@PutMapping(value= "/activate/{id}") 
	@ApiOperation(value = "Activates the merchant based on Id")
	public Merchant activateMerchantById(@PathVariable long id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - activate merchant by id is invoked.");

		Optional<Merchant> byId =  service.getMerchantById(id);
		Merchant merchant = byId.get();
		if(!byId.isPresent())
			throw new Exception("Could not find merchant with id- " + id);
		merchant.setIsActive(true);		
		return service.updateMerchant(merchant);
		//		service.deleteMerchantById(id);
	}

	@DeleteMapping(value= "/deleteall")
	@ApiOperation(value = "delete all merchants")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all merchants is invoked.");
		service.deleteAllMerchants();
	}
}