package com.westward.estore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.westward.estore.utils.DataSourceUtils;

public class PrivilegeDao {
	
	public PrivilegeDao() {
	}
	
	/**
	 * check role have the privilege
	 * */
	public boolean checkPrivilege(String role,String privilegeName){
		String sql= "select privileges.name from privileges,userprivilege,role where privileges.id=userprivilege.privilege_id and userprivilege.role=role.role and role.role= ?  ";
		QueryRunner queryRunner= new QueryRunner(DataSourceUtils.getDataSource());
		List<Object> privilegeNames= null;
		try {
			privilegeNames = queryRunner.query(sql, new ColumnListHandler(), role);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("当前用户的角色："+ role+ ",需要的权限:"+ privilegeName);;
		System.out.println("当前用户拥有的权限："+ privilegeNames);
		
		return privilegeNames.contains(privilegeName);
	}
	
}
