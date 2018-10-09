package com.syfri.exhibition.controller.prediction;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syfri.exhibition.model.prediction.QyzwyxVO;
import com.syfri.exhibition.service.prediction.QyzwyxService;
import com.syfri.baseapi.controller.BaseController;

@RestController
@RequestMapping("qyzwyx")
public class QyzwyxController  extends BaseController<QyzwyxVO>{

	@Autowired
	private QyzwyxService qyzwyxService;

	@Override
	public QyzwyxService getBaseService() {
		return this.qyzwyxService;
	}

}
