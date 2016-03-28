package com.whenling.extension.mdm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.whenling.centralize.model.User;
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

	public Page<Device> findByOwner(User owner, Pageable pageable) {
		return deviceRepository.findByOwner(owner, pageable);
	}
}
