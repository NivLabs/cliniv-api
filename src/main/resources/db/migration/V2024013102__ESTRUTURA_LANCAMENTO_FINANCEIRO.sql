CREATE TABLE `CATEG_LANC_FIN` (
                                `ID` varchar(100) not null,
                                `DESCRICAO` varchar(100) not null,
                                PRIMARY KEY (`ID`)
);

CREATE TABLE `CAB_LANC_FIN` (
                                       `ID` bigint NOT NULL AUTO_INCREMENT,
                                       `UUID` varchar(36) not null,
                                       `TITULO` varchar(200) DEFAULT NULL,
                                       `ID_FORNECEDOR` bigint NULL,
                                       `ID_PACIENTE` bigint NULL,
                                       `ID_CATEGORIA` varchar(100) NOT NULL,
                                       `VALOR_BRUTO` decimal(10, 2) DEFAULT NULL,
                                       `VALOR_LIQUIDO` decimal(10, 2) DEFAULT NULL,
                                       `TOTAL_DESCONTO` decimal(10, 2) DEFAULT NULL,
                                       `DT_COMPETENCIA` date NOT NULL,
                                       `DT_VENCIMENTO` date NOT NULL,
                                       `DH_PAGAMENTO` datetime NULL,
                                       `TIPO_LANCAMENTO` varchar(30) NOT NULL, -- RECEITA ou DESPESA
                                       `SITUACAO` varchar(50) NOT NULL, -- PAGO, CANCELADO, AGUARDANDO
                                       `OBSERVACAO` varchar(100) DEFAULT NULL,
                                       PRIMARY KEY (`ID`),
                                       UNIQUE KEY `UUID_FORNECEDOR_UNIQUE` (`UUID`),
                                       KEY `FK_LANC_FIN_FORNECEDOR_idx` (`ID_FORNECEDOR`),
                                       KEY `FK_LANC_FIN_PACIENTE_idx` (`ID_PACIENTE`),
                                       KEY `FK_LANC_FIN_CATEGORIA_idx` (`ID_CATEGORIA`),
                                       CONSTRAINT `FK_LANC_FIN_FORNECEDOR` FOREIGN KEY (`ID_FORNECEDOR`) REFERENCES `FORNECEDOR` (`ID`),
                                       CONSTRAINT `FK_LANC_FIN_PACIENTE` FOREIGN KEY (`ID_PACIENTE`) REFERENCES `PACIENTE` (`ID`),
                                       CONSTRAINT `FK_LANC_FIN_CATEGORIA` FOREIGN KEY (`ID_CATEGORIA`) REFERENCES `CATEG_LANC_FIN` (`ID`)
);