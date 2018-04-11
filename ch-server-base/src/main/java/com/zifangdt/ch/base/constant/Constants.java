package com.zifangdt.ch.base.constant;

/**
 * Created by 袁兵 on 2017/8/31.
 */
public interface Constants {
    String HTTP_HEADER_CALLED_BY = "X-Called-By";
    String CALLED_BY_FEIGN = "calledByFeign";

    String HTTP_HEADER_USER_ID = "X-User-Id";
    String HTTP_HEADER_USER_NAME = "X-User-Name";
    String HTTP_HEADER_USER_ACTUAL_NAME = "X-User-Actual-Name";
    String HTTP_HEADER_USER_AUTHORITIES = "X-User-Authorities";
    String HTTP_HEADER_REQUEST_URL = "X-Request-Url";
    String HTTP_HEADER_TENANT_ID = "X-Tenant-ID";
    String HTTP_HEADER_TENANT_DB_ADDRESS = "X-Tenant-DB-Address";

    Long ROOT_ORGANIZATION_ID = 1L;
    Long ADMIN_USER_ID = 1L;

    int YES = 1;
    int NO = 0;

    String FILE_MAX_SIZE = "20MB";
    String FILE_TOTAL_SIZE = "100MB";

    String SERVICE_UAA = "UAA-SERVER";

    int DEFAULT_PAGE_SIZE = 10;

    String IMPORT_FAILURE_FILE_NAME_USER = "用户导入失败记录";
    String IMPORT_FAILURE_FILE_EXT = ".xls";
    String IMPORT_TEMP_FILE_PREFIX = "import_";

    String PACKAGE_PREFIX = "com.zifangdt.ch.";
    String APPLICATION_NAME_SUFFIX = "-server";

    Long TYPE_OF_CONTRACT_PERIOD = 10000L;
    Long TYPE_OF_TICKET_REVENUE = 10001L;
    Long TYPE_OF_PURCHASE = 10002L;
    Long TYPE_OF_TICKET_EXPENSE = 10003L;
    Long TYPE_OF_PARTNER_COMMISSION = 10004L;
    Long TYPE_OF_CONTRACT_COMMISSION = 10005L;
    Long TYPE_OF_ADJUST = 10006L;
    Long TYPE_OF_ACCOUNT_TRANSFER = 9999L;
    String TYPE_NAME_OF_ACCOUNT_TRANSFER = "账户互转";

}
