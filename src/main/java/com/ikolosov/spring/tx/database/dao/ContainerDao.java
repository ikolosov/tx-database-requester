package com.ikolosov.spring.tx.database.dao;

import com.ikolosov.spring.tx.database.model.IContainer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author ikolosov
 */
public class ContainerDao extends JdbcDaoSupport implements IContainerDao<IContainer> {

	@Override
	public boolean insert(IContainer container) {
		String insertSql = "insert into T_CONTAINERS (ID, DESCR, VAL) values (?, ?, ?)";
		return getJdbcTemplate().update(
				insertSql,
				container.getId(),
				container.getDescription(),
				container.getValue()) > 0;
	}

	@Override
	public IContainer select(int id) {
		String selectSql = "select * from T_CONTAINERS where ID = ?";
		return getJdbcTemplate().queryForObject(
				selectSql,
				new Object[]{id},
				IContainer.createRowMapper());
	}

	@Override
	public boolean update(IContainer container) {
		String updateSql = "update T_CONTAINERS set VAL = ? where ID = ?";
		/**
		 * []
		 * this dummy exception is to emulate execution failure;
		 * 'magic number' id that causes a failure is 3
		 */
		if (container.getId() == 3)
			throw new RuntimeException("This is dummy exception has to trigger transaction rollback");
		return getJdbcTemplate().update(
				updateSql,
				container.getValue(),
				container.getId()) > 0;
	}

	@Override
	public boolean delete(IContainer container) {
		String deleteSql = "delete from T_CONTAINERS where ID = ?";
		return getJdbcTemplate().update(
				deleteSql,
				container.getId()) > 0;
	}

	/**
	 * []
	 * ddl statement that re-creates corresponding database table;
	 * its execution is to be triggered at bean init-time
	 */
	private void init() {
		try {
			String ddlStatement =
					"declare\n" +
					"  cnt number;\n" +
					"begin\n" +
					"  select count(*) into cnt from all_tables where table_name = 'T_CONTAINERS';\n" +
					"  if cnt <> 0 then\n" +
					"    execute immediate 'drop table T_CONTAINERS';\n" +
					"  end if;\n" +
					"  execute immediate 'create table T_CONTAINERS (ID number(3), DESCR varchar2(255), VAL number(12,2))';\n" +
					"end;\n";
			getJdbcTemplate().execute(ddlStatement);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
		}
	}
}
