package com.whenling.extension.mdm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.extension.mdm.model.Device;
import com.whenling.extension.mdm.repo.DeviceRepository;
import com.whenling.module.domain.service.BaseService;

@Service
public class DeviceService extends BaseService<Device, Long> {

	@Autowired
	private DeviceRepository deviceRepository;

	public Device findByUdid(String udid) {
		return deviceRepository.findByUdid(udid);
	}
}
