ALTER TABLE `PARAMETROS_LAYOUT_RELATORIO` 
ADD COLUMN `DESCRICAO` VARCHAR(200) NULL AFTER `TIPO`,
ADD COLUMN `VALOR_PADRAO` VARCHAR(5000) NULL AFTER `DESCRICAO`;