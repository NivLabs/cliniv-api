UPDATE `PERMISSAO` SET `NOME` = 'ROLE_ATENDIMENTO_LEITURA', `DESCRICAO` = 'Permissão de leitura em prontuário' WHERE `ID` = 2;

INSERT INTO `PERMISSAO` (`ID`, `NOME`, `DESCRICAO`) VALUES (3, 'ROLE_ATENDIMENTO_ESCRITA', 'Permissão de escrita em prontuário');


INSERT INTO `PERMISSAO` (`ID`, `NOME`, `DESCRICAO`) VALUES (4, 'ROLE_PACIENTE_LEITURA', 'Permissão de leitura em paciente');
INSERT INTO `PERMISSAO` (`ID`, `NOME`, `DESCRICAO`) VALUES (5, 'ROLE_PACIENTE_ESCRITA', 'Permissão de escrita em paciente');

INSERT INTO `PERMISSAO` (`ID`, `NOME`, `DESCRICAO`) VALUES (6, 'ROLE_PROFISSIONAL_LEITURA', 'Permissão de leitura em profissional');
INSERT INTO `PERMISSAO` (`ID`, `NOME`, `DESCRICAO`) VALUES (7, 'ROLE_PROFISSIONAL_ESCRITA', 'Permissão de escrita em profissional');

INSERT INTO `PERMISSAO` (`ID`, `NOME`, `DESCRICAO`) VALUES (8, 'ROLE_EVENTO_LEITURA', 'Permissão de leitura em evento');
INSERT INTO `PERMISSAO` (`ID`, `NOME`, `DESCRICAO`) VALUES (9, 'ROLE_EVENTO_ESCRITA', 'Permissão de escrita em evento');

INSERT INTO `PERMISSAO` (`ID`, `NOME`, `DESCRICAO`) VALUES (10, 'ROLE_SETOR_LEITURA', 'Permissão de leitura em setor');
INSERT INTO `PERMISSAO` (`ID`, `NOME`, `DESCRICAO`) VALUES (11, 'ROLE_SETOR_ESCRITA', 'Permissão de escrita em setor');
