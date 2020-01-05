UPDATE `devdb`.`TIPO_EVENTO` SET `DESCRICAO`= 'Entrada do paciente na emergência'
WHERE
   `ID`= '2';
UPDATE `devdb`.`TIPO_EVENTO` SET `DESCRICAO`= 'Entrada do paciente'
WHERE
   `ID`= '1';
UPDATE `devdb`.`TIPO_EVENTO` SET `ID_TIPO_EVENTO`= null
WHERE
   `ID`= '4';
UPDATE `devdb`.`TIPO_EVENTO` SET `ID_TIPO_EVENTO`= null
WHERE
   `ID`= '5';