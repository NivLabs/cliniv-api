ALTER TABLE `ANAMNESE_ITEM` 
CHANGE COLUMN `SIM_NAO` `SIM_NAO` VARCHAR(3) NOT NULL DEFAULT 'NAO' ;

ALTER TABLE `ANAMNESE_ITEM` 
CHANGE COLUMN `PERGUNTA` `PERGUNTA` VARCHAR(200) NOT NULL ;

INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('O que sente?');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('Quando começou a sentir?');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('Como começou? (súbito ou progressivo)?');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('Como evoluiu? (como estava antes e como está agora)');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('Qual o tipo da dor? (queimação, pontada, pulsátil, cólica, constritiva, contínua, cíclica, profunda, superficial)');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('Qual a duração da crise? (se a dor for cíclica)');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('É uma dor que se espalha ou não?');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('Qual a intensidade da dor? (forte, fraca ou usar escala de 1 a 10).');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('A dor impede a realização de alguma tarefa?');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('Em que hora do dia ela é mais forte?');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('Existe alguma coisa que o sr. faça que a dor melhore?');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('E que piora?');
INSERT INTO `ANAMNESE_ITEM` (`PERGUNTA`) VALUES ('A dor é acompanhada de mais algum sintoma?');
