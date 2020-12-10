package com.user.UserServiceMs.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.user.UserServiceMs.dto.BuyerDTO;
import com.user.UserServiceMs.dto.BuyerLoginDTO;
import com.user.UserServiceMs.dto.ProductDTO;
import com.user.UserServiceMs.dto.SellerDTO;
import com.user.UserServiceMs.dto.SellerLoginDTO;
import com.user.UserServiceMs.dto.WishlistDTO;
import com.user.UserServiceMs.service.UserServiceMSservice;

//controller
@RestController
@CrossOrigin
public class UserServiceController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserServiceMSservice userService;
	
	@Autowired
	private Environment environment;
	
	@Value("${product.uri}")
	String productUri;
	
	// Create a new buyer
		@PostMapping(value = "/buyer/register",  consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> createBuyer(@RequestBody BuyerDTO buyerDTO) throws Exception{
			logger.info("Creation request for buyer {}", buyerDTO);
			try {
				Integer buyerId=userService.createBuyer(buyerDTO);
				String successMessage=environment.getProperty("BUYER_SUCCESS")+buyerId;
				return new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
			}
			catch(Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()),e);
			}
		}
		
		// Login buyer
		@PostMapping(value = "/buyer/login",consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Boolean> login(@RequestBody BuyerLoginDTO buyerLoginDTO) {
			try {
				logger.info("Login request for buyer {} with password {}", buyerLoginDTO.getEmail(),buyerLoginDTO.getPassword());
				Boolean check=userService.buyerLogin(buyerLoginDTO);
				return new ResponseEntity<Boolean>(check,HttpStatus.NOT_FOUND);
			}
			catch(Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()),e);
			}
		}
		// finding buyer
				@GetMapping(value = "/buyer/{buyerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity<Boolean> findBuyer(@PathVariable Integer buyerId) {
					try {
						logger.info("find request for buyer {} with buyerId{}",buyerId);
						Boolean check=userService.findBuyerId(buyerId);
						return new ResponseEntity<Boolean>(check,HttpStatus.NOT_FOUND);
					}
					catch(Exception e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()),e);
					}
				}
		
		// Create a new seller
				@PostMapping(value = "/seller/register",  consumes = MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity<String> createSeller(@RequestBody SellerDTO sellerDTO) throws Exception{
					logger.info("Creation request for seller {}", sellerDTO);
					try {
						Integer sellerId=userService.createSeller(sellerDTO);
						String successMessage=environment.getProperty("Seller_SUCCESS")+sellerId;
						return new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
					}
					catch(Exception e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()),e);
					}
				}
				// Login seller
				@PostMapping(value = "/seller/login",consumes = MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity<Boolean> sellerlogin(@RequestBody SellerLoginDTO sellerLoginDTO) {
					try {
						logger.info("Login request for seller {} with password {}", sellerLoginDTO.getsEmail(),sellerLoginDTO.getsPassword());
						Boolean check=userService.sellerLogin(sellerLoginDTO);
						return new ResponseEntity<Boolean>(check,HttpStatus.NOT_FOUND);
					}
					catch(Exception e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()),e);
					}
				}
				// finding seller
				@GetMapping(value = "/seller/{sellerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity<Boolean> findSeller(@PathVariable Integer sellerId) {
					try {
						logger.info("find request for seller {} with sellerId{}",sellerId);
						Boolean check=userService.findSellerId(sellerId);
						return new ResponseEntity<Boolean>(check,HttpStatus.FOUND);
					}
					catch(Exception e) {
						throw new ResponseStatusException(HttpStatus.NOT_FOUND,environment.getProperty(e.getMessage()),e);
					}
				}
				//deactivate seller
				@DeleteMapping(value = "/seller/deactivate/{sellerId}")
			    public ResponseEntity<Boolean> deactivateSeller(@PathVariable Integer sellerId) {
					try {
						logger.info("deactivate request for seller {} with sellerId{}",sellerId);
						Boolean check=userService.deactivateSeller(sellerId);
						return new ResponseEntity<Boolean>(check,HttpStatus.FOUND);
					}
					catch(Exception e) {
						throw new ResponseStatusException(HttpStatus.NOT_FOUND,environment.getProperty(e.getMessage()),e);
					}
			        
			    }
				// activate seller
				@PostMapping(value = "/seller/activate",consumes = MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity<Boolean> sellerActivate(@RequestBody SellerLoginDTO sellerLoginDTO) {
					try {
						logger.info("Activate request for seller {} with password {}", sellerLoginDTO.getsEmail(),sellerLoginDTO.getsPassword());
						Boolean check=userService.activateSeller(sellerLoginDTO);
						return new ResponseEntity<Boolean>(check,HttpStatus.OK);
					}
					catch(Exception e) {
						throw new ResponseStatusException(HttpStatus.NOT_MODIFIED,environment.getProperty(e.getMessage()),e);
					}
				}
				
				//add product to wishlist
				@PostMapping(value = "/buyer/addToWishlist",consumes = MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity<String> addProductToWishlist(@RequestBody WishlistDTO wishlistDTO){
					try {
						//Boolean flag = false;
						String successMessage = environment.getProperty("BUYER_WISHLIST_SUCCESS");
						//String errorMessage = environment.getProperty("BUYER_WISHLIST_ERROR");
						ProductDTO product = new RestTemplate().getForObject("http://localhost:8000/getProduct/" + wishlistDTO.getProductId(), ProductDTO.class);
						Integer prodid = userService.addProductToWishlist(wishlistDTO);
						return new ResponseEntity<String>(successMessage + " Product Name : " + product.getProductname(), HttpStatus.OK);
					}
					catch(Exception e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()),e);
					}
				}
				
				//remove product from wishlist
				@DeleteMapping(value = "/buyer/removeProductFromWishlist/{buyerId}/{productId}")
			    public ResponseEntity<Boolean> removeProductFromWishlist(@PathVariable Integer buyerId, @PathVariable Integer productId) {
					try {
						//logger.info("deactivate request for seller {} with sellerId{}",sellerId);
						Boolean check=userService.removeProductFromWishlist(buyerId, productId);
						return new ResponseEntity<Boolean>(check,HttpStatus.FOUND);
					}
					catch(Exception e) {
						throw new ResponseStatusException(HttpStatus.NOT_FOUND,environment.getProperty(e.getMessage()),e);
					}
			        
			    }

}
