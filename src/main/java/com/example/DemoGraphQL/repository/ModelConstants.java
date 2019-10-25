package com.example.DemoGraphQL.repository;

class ModelConstants {
 
	static class METER_READING {
		
        static final String APP_LABEL = "Rhycom_Application";
        static final String CUSTOMER_LABEL = "Customer";
        static final String METER_LABEL = "MeterDetails";
        static final String READING_LABEL = "Readings";

        static final String APP_NAME = "name";
        static final String CREATED_DATE = "created_date";
        
        static final String CUSTOMER_NAME = "customer_name";
        static final String CUSTOMER_ADDRESS = "customer_address";

  
        
        static final String METER_ID = "meterId";
        static final String METER_TRANSACTION_DATE = "trans_date";

        
        	static final String READING_TIMESTAMP = "timestamp";
        static final String READING_KWH = "KWh";
        static final String READING_KW = "KW";
        static final String READING_KVA = "KVA";
    }
	
    static class PERSON {
        static final String LABEL = "Person";
        static final String BIRTH_DATE = "birthDate";
        static final String FIRST_NAME = "firstName";
        static final String LAST_NAME = "lastName";
        static final String GENDER = "gender";
    }

    static class RELATIONS {
        static final String MOTHER = "isMotherOf";
        static final String FATHER = "isFatherOf";
    }
}
