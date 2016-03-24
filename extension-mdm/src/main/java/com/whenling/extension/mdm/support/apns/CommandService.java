package com.whenling.extension.mdm.support.apns;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whenling.extension.mdm.model.Device;
import com.whenling.extension.mdm.support.payload.command.CommandPayload;

@Component
public class CommandService {

	@Autowired
	private WakeupService wakeupService;

	public void send(CommandPayload command, Device... devices) {
		push(command, null, devices);
		wakeupService.wake(devices);
	}

	public void send(CommandPayload command, CommandCallback callback, Device... devices) {
		push(command, callback, devices);
		wakeupService.wake(devices);
	}

	public CommandContext get(String uuid) {
		return commands.get(uuid);
	}

	public CommandContext remove(String uuid) {
		return commands.remove(uuid);
	}

	public CommandContext getAndRemoveCommand(Device device) {
		CommandContext commandContext = pop(device);
		if (commandContext != null) {
			commands.put(commandContext.getUuid(), commandContext);
		}
		return commandContext;
	}

	private Map<String, Queue<CommandContext>> data = new HashMap<>();

	private Map<String, CommandContext> commands = new HashMap<>();

	protected void push(CommandPayload command, CommandCallback callback, Device... devices) {

		for (Device device : devices) {
			Queue<CommandContext> commands = data.get(device.getUdid());
			if (commands == null) {
				commands = new ConcurrentLinkedQueue<>();
				data.put(device.getUdid(), commands);
			}

			CommandContext commandContext = new CommandContext();
			commandContext.setUuid(UUID.randomUUID().toString());
			commandContext.setDevice(device);
			commandContext.setPayload(command);
			commandContext.setDevice(device);
			commandContext.setCallback(callback);

			commands.add(commandContext);
		}
	}

	protected CommandContext pop(Device device) {
		Queue<CommandContext> commands = data.get(device.getUdid());
		if (commands == null) {
			return null;
		}

		return commands.poll();

	}
}
