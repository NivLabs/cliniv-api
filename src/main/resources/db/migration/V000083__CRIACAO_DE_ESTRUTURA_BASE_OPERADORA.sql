CREATE TABLE OPERADORA (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `RAZAO_SOCIAL` VARCHAR(300) NOT NULL,
  `COD_ANS` BIGINT(20) NOT NULL,
  `CNPJ` VARCHAR(14) NOT NULL,
  `NOME_FANTASIA` VARCHAR(300) NOT NULL,
  `MODALIDADE` VARCHAR(45) NOT NULL COMMENT 'Operadoras possíveis:\n- Operadoras de planos de saúde\n- Cooperativa médica/odontológica\n- Autogestão\n- Filantropia\n- Administradora\n- Seguradoras de saúde',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `COD_ANS_UK` (`COD_ANS` ASC),
  UNIQUE KEY `CNPJ_UK` (`CNPJ` ASC));