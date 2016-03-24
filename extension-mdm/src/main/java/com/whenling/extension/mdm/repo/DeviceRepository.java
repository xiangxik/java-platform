package com.whenling.extension.mdm.repo;

import com.whenling.extension.mdm.model.Device;
import com.whenling.module.domain.repository.BaseRepository;

public interface DeviceRepository extends BaseRepository<Device, Long> {

	Device findByUdid(String udid);
}
