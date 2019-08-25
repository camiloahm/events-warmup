package com.sqs.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SQSController {
	
	private static final Logger log = LoggerFactory.getLogger(SQSController.class);

	@Value("${aws.sns.topic.demo.ARN}")
	String snsTopicDemoARN;

	private AmazonSNS amazonSNS;

	@Autowired
	public SQSController() {
		this.amazonSNS = AmazonSNSClientBuilder.standard().
				withRegion(Regions.EU_CENTRAL_1)
				.build();
	}
	
	@RequestMapping(value = "/sendMessageQueue", method = RequestMethod.POST)
    public @ResponseBody void write(@RequestBody String notificationData){
		try {

		log.info("===============================================");
		log.info("Getting Started with Amazon SQS Standard Queues");
		log.info("===============================================\n");
        
		log.info("Sending a message to MyQueue.\n");
			PublishRequest publishRequest = new PublishRequest(snsTopicDemoARN, notificationData, "test");
			PublishResult publishResult = this.amazonSNS.publish(publishRequest); ;
        log.info("Message Sent.\n");

		} catch (final Exception ase) {
			log.error("Caught an AmazonServiceException, which means " +
                    "your request made it to Amazon SQS, but was " +
                    "rejected with an error response for some reason.");
			log.error("Error Message:    " + ase.getMessage());
        }
    }
}
