ALTER TABLE `INSTITUTO` 
CHANGE COLUMN `CNES` `CNES` VARCHAR(45) NULL ,
CHANGE COLUMN `NATUREZA_LEGAL` `NATUREZA_LEGAL` VARCHAR(80) NULL ,
CHANGE COLUMN `COMPLEMENTO` `COMPLEMENTO` VARCHAR(45) NULL ,
CHANGE COLUMN `DEPENDENCIA` `DEPENDENCIA` VARCHAR(45) NULL ,
CHANGE COLUMN `TIPO_DE_INSTITUICAO` `TIPO_DE_INSTITUICAO` VARCHAR(45) NULL ,
CHANGE COLUMN `GESTAO` `GESTOR` VARCHAR(80) NULL ,
CHANGE COLUMN `CHAVE_ACESSO` `CHAVE_ACESSO` VARCHAR(80) NULL ,
ADD COLUMN `TELEFONE_GESTOR` VARCHAR(45) NULL AFTER `GESTOR`,
ADD COLUMN `EMAIL_GESTOR` VARCHAR(80) NULL AFTER `TELEFONE_GESTOR`;
