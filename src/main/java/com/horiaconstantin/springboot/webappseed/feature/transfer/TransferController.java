package com.horiaconstantin.springboot.webappseed.feature.transfer;

import com.horiaconstantin.springboot.webappseed.service.TransferService;
import com.horiaconstantin.springboot.webappseed.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransferController {
	public static final String TRANSFER = "/transfer";
	private final TransferService transferService;
	private final UserService userService;

	public TransferController(TransferService transferService, UserService userService) {
		this.transferService = transferService;
		this.userService = userService;
	}

	@PostMapping(value = TRANSFER)
	public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequest transfer) {
		String receiverUsername = transfer.getReceiverUsername();
		long transferredAmount = transferService.transfer(transfer.getAmount(), receiverUsername);

		TransferResponse response = new TransferResponse()
				.setAmount(transferredAmount)
				.setReceiver(receiverUsername)
				.setSender(userService.getCurrentUser().getUsername());
		return ResponseEntity.ok(response);
	}
}