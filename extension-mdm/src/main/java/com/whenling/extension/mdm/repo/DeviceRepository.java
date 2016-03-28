package com.whenling.extension.mdm.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whenling.centralize.model.User;
import com.whenling.extension.mdm.model.Device;
import com.whenling.module.domain.repository.BaseRepository;

public interface DeviceRepository extends BaseRepository<Device, Long> {

	Device findByUdid(String udid);

	Page<Device> findByOwner(User owner, Pageable pageable);
}
