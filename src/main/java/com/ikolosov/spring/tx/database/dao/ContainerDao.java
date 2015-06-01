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
		String insertSql = "INSERT INTO T_CONTAINERS (ID, DESCR, VAL) VALUES(?, ?, ?)";
		return getJdbcTemplate().update(
				insertSql,
				container.getId(),
				container.getDescription(),
				container.getValue()) > 0;
	}

	@Override
	public IContainer select(int id) {
		String selectSql = "SELECT * FROM T_CONTAINERS WHERE ID = ?";
		return getJdbcTemplate().queryForObject(
				selectSql,
				new Object[]{id},
				IContainer.createRowMapper());
	}

	@Override
	public boolean update(IContainer container) {
		String updateSql = "UPDATE T_CONTAINERS SET VAL = ? where ID = ?";
		/**
		 * []
		 * this dummy exception is to emulate execution failure
		 */
		if (container.getId() == 3)
			throw new RuntimeException("Dummy exception that should trigger transaction rollback");
		return getJdbcTemplate().update(
				updateSql,
				container.getValue(),
				container.getId()) > 0;
	}

	@Override
	public boolean delete(IContainer container) {
		String deleteSql = "DELETE FROM T_CONTAINERS WHERE ID = ?";
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
					"DECLARE\n" +
					"  cnt NUMBER;\n" +
					"BEGIN\n" +
					"  SELECT count(*) INTO cnt FROM all_tables WHERE table_name = 'T_CONTAINERS';\n" +
					"  IF cnt <> 0 THEN\n" +
					"    EXECUTE IMMEDIATE 'DROP TABLE T_CONTAINERS';\n" +
					"  END IF;\n" +
					"  EXECUTE IMMEDIATE 'CREATE TABLE T_CONTAINERS (ID NUMBER(3), DESCR VARCHAR(255), VAL NUMBER(12,2))';\n" +
					"END;\n";
			getJdbcTemplate().execute(ddlStatement);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
		}
	}
}
