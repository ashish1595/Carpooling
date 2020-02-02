package com.carpool.server.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpool.server.dto.NotificationDTO;
import com.carpool.server.dto.NotificationCountDTO;
import com.carpool.server.exception.ResponseCode;
import com.carpool.server.exception.UtilityException;
import com.carpool.server.repository.NotificationRepository;
import com.carpool.server.service.NotificationService;
import com.carpool.server.utils.CommonUtility;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public List<NotificationDTO> getNotifications(String userId) throws UtilityException {
		List<NotificationDTO> notificationList = notificationRepository.findByTargetUserId(userId);
		if (! CommonUtility.isValidList(notificationList)) throw new UtilityException(ResponseCode.USER_DATA_NOT_FOUND_IN_RESPONSE);
		return notificationList;
	}
	
	@Override
	public Boolean changeReadFlag(String notificationId) throws UtilityException {
		NotificationDTO notificationDTO = notificationRepository.findById(notificationId).get();
		notificationDTO.setReadFlag(1);
		notificationRepository.save(notificationDTO);
		return Boolean.TRUE;
	}

	@Override
	public NotificationCountDTO getNotificationCount(String userId) throws UtilityException {
		// TODO Auto-generated method stub
		return null;
	}
	
}