ALTER TABLE `USUARIO_PERMISSAO` 
DROP FOREIGN KEY `fk_PERMISSAO_USUARIO`;
ALTER TABLE `USUARIO_PERMISSAO` 
DROP INDEX `fk_PERMISSAO_USUARIO_idx` ;

 TRUNCATE TABLE `USUARIO_PERMISSAO`;
 TRUNCATE TABLE `PERMISSAO`;

ALTER TABLE `PERMISSAO` 
CHANGE COLUMN `DESCRICAO` `DESCRICAO` VARCHAR(80) NOT NULL ,
ADD COLUMN `NOME` VARCHAR(45) NOT NULL AFTER `ID`,
ADD UNIQUE INDEX `NOME_UNIQUE` (`NOME` ASC),
DROP INDEX `DESCRICAO_UNIQUE` ;

ALTER TABLE `USUARIO_PERMISSAO` 
ADD CONSTRAINT `fk_PERMISSAO_USUARIO`
  FOREIGN KEY (`ID_PERMISSAO`)
  REFERENCES `PERMISSAO` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


INSERT INTO `PERMISSAO` VALUES (1, 'ROLE_ADMIN', 'Acesso de administrador do sistema');
INSERT INTO `PERMISSAO` VALUES (2, 'ROLE_COMUM', 'Acesso às funcionalidades básicas');

INSERT INTO `USUARIO_PERMISSAO` VALUES (1, 1);
INSERT INTO `USUARIO_PERMISSAO` VALUES (1, 2);