package com.ericsson.ibp.configuration.VO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IBPProductConfiguration_RowMapper implements RowMapper<IBPProductConfiguration_Row>{

	public IBPProductConfiguration_Row mapRow(ResultSet rsltSet, int rowNum) throws SQLException {
		
		IBPProductConfiguration_Row objIbpProdConfig=new IBPProductConfiguration_Row();
		objIbpProdConfig.setProductConfigId(rsltSet.getLong("PRODUCT_CONFIG_ID"));
		objIbpProdConfig.setProviderOfferId(rsltSet.getInt("PROVIDER_OFFER_ID"));
		objIbpProdConfig.setConsumerOfferId(rsltSet.getInt("CONSUMER_OFFER_ID"));
		objIbpProdConfig.setOfferType(rsltSet.getInt("OFFER_TYPE"));
		objIbpProdConfig.setProductName(rsltSet.getString("PRODUCT_NAME"));
		objIbpProdConfig.setTmcode(rsltSet.getString("TMCODE"));
		objIbpProdConfig.setSncode(rsltSet.getString("SNCODE"));
		objIbpProdConfig.setProductType(rsltSet.getInt("PRODUCT_TYPE"));
		objIbpProdConfig.setContributingTariff(rsltSet.getInt("CONTRIBUTING_TARIFF"));
		objIbpProdConfig.setConsumingTariff(rsltSet.getInt("CONSUMING_TARIFF"));
		objIbpProdConfig.setConsumingSn(rsltSet.getInt("CONSUMING_SN"));
		objIbpProdConfig.setDefaultQos(rsltSet.getInt("DEFAULT_QOS"));
		objIbpProdConfig.setUnthrottleQosList(rsltSet.getString("UNTHROTTLE_QOS_LIST"));
		objIbpProdConfig.setProductTypeName(rsltSet.getString("PRODUCT_TYPE_NAME"));
		return objIbpProdConfig;		
	}

}
