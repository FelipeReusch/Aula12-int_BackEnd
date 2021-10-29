package com.backend.aula12;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.h2.Driver;

import java.sql.*;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class); // cria objeto de log para a classe

    private static final String createSql = "CREATE TABLE funcionarios (Id INT PRIMARY KEY, " +
            "primeiroNome VARCHAR2 NOT NULL, " +
            "sobrenome VARCHAR2 NOT NULL, " +
            "idade INT NOT NULL, " +
            "genero VARCHAR2 NOT NULL)";

    private static final String dropSql = " DROP TABLE IF EXISTS funcionarios "; // criando a tabela

    // criando as variaveis
    private static final String insertSql = "INSERT INTO funcionarios (Id, primeiroNome, sobrenome, idade, genero)" +
            "VALUES (1, 'Adryana', 'Portugal',21, 'feminino')";
    private static final String insertSql2 = "INSERT INTO funcionarios (Id, primeiroNome, sobrenome, idade, genero)" +
            "VALUES (2, 'Felipe', 'Rosa',34, 'masculino')";
    private static final String insertSql3 = "INSERT INTO funcionarios (Id, primeiroNome, sobrenome, idade, genero)" +
            "VALUES (2, 'Vinicius', 'Mendes',18, 'masculino')";
    private static final String insertSql4 = "INSERT INTO funcionarios (Id, primeiroNome, sobrenome, idade, genero)" +
            "VALUES (4, 'Joana', 'Madeira',25, 'feminino')";

    private static final String selectSql = "SELECT * FROM funcionarios";

    private static final String updateSql = "UPDATE funcionarios SET primeiroNome='Felipi' Where id=2";
    private static final String deleteSql = "DELETE FROM funcionarios WHERE id= 1";



    public static void main(String[] args) {

        PropertyConfigurator.configure("log4j.properties");

        Connection connection;

        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(dropSql); // nao criar 2 tabelas iguais
            statement.execute(createSql); // criar tabela

            try {
                statement.execute(insertSql);
                statement.execute(insertSql2);
                statement.execute(insertSql3);
                statement.execute(insertSql4);

            }catch (SQLException e){
                logger.error("|| CANNOT REPEAT AN ID ||" + e.getMessage());
            }
            showFuncionario(connection);
            statement.execute(updateSql);
            ResultSet resultado = statement.executeQuery("SELECT * FROM funcionarios WHERE id=2");
            while(resultado.next()){
                logger.debug("O Funcionario: " + resultado.getString(2) + " foi atualizado!");
            }
            statement.execute(deleteSql);
            logger.info("Delete funcionario id=1");
            statement.execute("DELETE FROM funcionarios WHERE primeiroNome= 'Adryana'");
            logger.info("O funcionario Felipe foi deletado");
            showFuncionario(connection);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void showFuncionario(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(selectSql);
        while (result.next()) {
            logger.info("Id: "+ result.getString(1) +" primeiroNome: " + result.getString(2) + " sobrenome: "+result.getString(3) + " idade: " +result.getInt(4) + " genero: " + result.getString(5));
        }
    }

    public static Connection getConnection () throws Exception {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

    }

}
