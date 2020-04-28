package com.znlhzl.databus.service.impl;

import com.znlhzl.databus.entity.MetadataConsume;
import com.znlhzl.databus.mapper.MetadataConsumeMapper;
import com.znlhzl.databus.service.IMetadataConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataConsumeServiceImpl implements IMetadataConsumeService {

	@Autowired
	MetadataConsumeMapper metadataConsumeMapper;

	@Override
	public void save(MetadataConsume metadataConsume) {
		metadataConsumeMapper.insertSelective(metadataConsume);
	}
}
