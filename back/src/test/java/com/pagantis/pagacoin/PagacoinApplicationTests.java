package com.pagantis.pagacoin;

import com.pagantis.pagacoin.model.Transfer;
import com.pagantis.pagacoin.model.User;
import com.pagantis.pagacoin.model.Wallet;
import com.pagantis.pagacoin.repository.TransferRepository;
import com.pagantis.pagacoin.repository.UserRepository;
import com.pagantis.pagacoin.repository.WalletRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@SpringBootTest
class PagacoinApplicationTests {

	@Test
	void contextLoads() {

	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	TransferRepository transferRepository;

	@Autowired
	WalletRepository walletRepository;

	/**
	 * testGetUsers
	 * @throws URISyntaxException
	 */
	@Test
	@Disabled
	public void testGetUsers() throws URISyntaxException
	{
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + 8080 + "/user/getAllUsers";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		//Verify request succeed
		Assert.isTrue(200 == result.getStatusCodeValue(), "");
	}

	/**
	 * testCreateAndFindNewUser
	 */
	@Test
	@Disabled
	public void testCreateAndFindNewUser()
	{
		userRepository.save(new User("test@gmail.com", "test123", "Javier", "Lopez Garcia"));

		Optional<User> user = userRepository.findByEmail("test@gmail.com");

		Assert.isTrue(user.get().getName().equals("Javier"),"");
	}

	/**
	 * testMakeTransfer
	 * @throws URISyntaxException
	 */
	@Test
	@Disabled
	public void testMakeTransfer() throws URISyntaxException
	{
		Optional<Wallet> sourcewalletData = walletRepository.findById("5fd3313780bda57d506d46ec");
		Double moneySource = sourcewalletData.get().getMoney();
		Optional<Wallet> destinationWalletData = walletRepository.findById("5fd2983758e1fe0d0ae80ea3");
		Double moneyDestination = destinationWalletData.get().getMoney();
		Transfer transfer = new Transfer("5fd3313780bda57d506d46ec", "5fd2983758e1fe0d0ae80ea3", 1.0);

		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + 8080 + "/transfer/makeTransfer";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
		HttpEntity<Transfer> request = new HttpEntity<>(transfer, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

		sourcewalletData = walletRepository.findById("5fd3313780bda57d506d46ec");
		destinationWalletData = walletRepository.findById("5fd2983758e1fe0d0ae80ea3");

		//Verify request succeed
		Assert.isTrue(201 == result.getStatusCodeValue(), "");
		Assert.isTrue(sourcewalletData.get().getMoney() == (moneySource - transfer.getMoney()),"");
		Assert.isTrue(destinationWalletData.get().getMoney() == (moneyDestination + transfer.getMoney()),"");
	}

}
