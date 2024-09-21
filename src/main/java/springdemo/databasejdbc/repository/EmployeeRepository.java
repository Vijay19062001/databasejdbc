package springdemo.databasejdbc.repository;


import springdemo.databasejdbc.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapper for Employee
    private RowMapper<Employee> rowMapper = new RowMapper<>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Employee(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
            );
        }
    };

    // Create employee
    public int save(Employee employee) {
        return jdbcTemplate.update("INSERT INTO employe (name, department, salary) VALUES (?, ?, ?)",
                employee.getName(), employee.getDepartment(), employee.getSalary());
    }

    // Read employee by id
    public Employee findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM employe WHERE id = ?", new Object[]{id}, rowMapper);
    }

    // Read all employees
    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employe", rowMapper);
    }

    // Update employee
    public int update(Employee employee) {
        return jdbcTemplate.update("UPDATE employe SET name = ?, department = ?, salary = ? WHERE id = ?",
                employee.getName(), employee.getDepartment(), employee.getSalary(), employee.getId());
    }

    // Delete employee
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM employe WHERE id = ?", id);
    }
}
