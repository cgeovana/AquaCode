-- Script de dados iniciais para AquaCode
-- Compatível com Hibernate import.sql (executado após drop-and-create)

-- ESPÉCIES MARINHAS
INSERT INTO especies_marinhas (nomeComum, nomeCientifico, reino, filo, classe, ordem, familia, genero, descricao, habitat, distribuicaoGeografica, dieta, statusConservacao, categoria, caracteristicasDistintivas) VALUES ('Golfinho-nariz-de-garrafa', 'Tursiops truncatus', 'Animalia', 'Chordata', 'Mammalia', 'Cetacea', 'Delphinidae', 'Tursiops', 'Golfinho de porte médio, altamente inteligente e social.', 'Águas costeiras temperadas e tropicais', 'Oceanos em todo o mundo', 'Peixes, lulas e crustáceos', 'LC', 'Mamífero', 'Focinho alongado em forma de garrafa');

INSERT INTO especies_marinhas (nomeComum, nomeCientifico, reino, filo, classe, ordem, familia, genero, descricao, habitat, distribuicaoGeografica, dieta, statusConservacao, categoria, caracteristicasDistintivas) VALUES ('Baleia-jubarte', 'Megaptera novaeangliae', 'Animalia', 'Chordata', 'Mammalia', 'Cetacea', 'Balaenopteridae', 'Megaptera', 'Grande baleia conhecida por seus cantos e saltos.', 'Oceanos profundos, migra para águas rasas', 'Todos os oceanos', 'Krill e pequenos peixes', 'LC', 'Mamífero', 'Nadadeiras peitorais longas, corcova dorsal');

INSERT INTO especies_marinhas (nomeComum, nomeCientifico, reino, filo, classe, ordem, familia, genero, descricao, habitat, distribuicaoGeografica, dieta, statusConservacao, categoria, caracteristicasDistintivas) VALUES ('Peixe-palhaço', 'Amphiprion ocellaris', 'Animalia', 'Chordata', 'Actinopterygii', 'Perciformes', 'Pomacentridae', 'Amphiprion', 'Pequeno peixe de recife com relação simbiótica com anêmonas.', 'Recifes de coral do Indo-Pacífico', 'Austrália, Sudeste Asiático, Japão', 'Zooplâncton, algas', 'LC', 'Peixe', 'Coloração laranja com três listras brancas');

INSERT INTO especies_marinhas (nomeComum, nomeCientifico, reino, filo, classe, ordem, familia, genero, descricao, habitat, distribuicaoGeografica, dieta, statusConservacao, categoria, caracteristicasDistintivas) VALUES ('Tubarão-baleia', 'Rhincodon typus', 'Animalia', 'Chordata', 'Chondrichthyes', 'Orectolobiformes', 'Rhincodontidae', 'Rhincodon', 'O maior peixe do mundo, inofensivo para humanos.', 'Águas tropicais e temperadas quentes', 'Oceanos tropicais em todo o mundo', 'Plâncton, krill, pequenos peixes', 'EN', 'Peixe', 'Padrão de manchas único, pode atingir 18 metros');

INSERT INTO especies_marinhas (nomeComum, nomeCientifico, reino, filo, classe, ordem, familia, genero, descricao, habitat, distribuicaoGeografica, dieta, statusConservacao, categoria, caracteristicasDistintivas) VALUES ('Tartaruga-verde', 'Chelonia mydas', 'Animalia', 'Chordata', 'Reptilia', 'Testudines', 'Cheloniidae', 'Chelonia', 'Grande tartaruga marinha herbívora na fase adulta.', 'Águas costeiras tropicais e subtropicais', 'Oceanos Atlântico, Pacífico e Índico', 'Algas e gramas marinhas (adultos)', 'EN', 'Réptil', 'Carapaça oval, coloração marrom a verde-oliva');

INSERT INTO especies_marinhas (nomeComum, nomeCientifico, reino, filo, classe, ordem, familia, genero, descricao, habitat, distribuicaoGeografica, dieta, statusConservacao, categoria, caracteristicasDistintivas) VALUES ('Polvo-comum', 'Octopus vulgaris', 'Animalia', 'Mollusca', 'Cephalopoda', 'Octopoda', 'Octopodidae', 'Octopus', 'Cefalópode altamente inteligente, capaz de mudar de cor.', 'Fundos rochosos e recifes até 200m', 'Oceanos tropicais e temperados', 'Crustáceos, moluscos e pequenos peixes', 'LC', 'Invertebrado', 'Oito braços com ventosas, capacidade de camuflagem');

INSERT INTO especies_marinhas (nomeComum, nomeCientifico, reino, filo, classe, ordem, familia, genero, descricao, habitat, distribuicaoGeografica, dieta, statusConservacao, categoria, caracteristicasDistintivas) VALUES ('Estrela-do-mar-comum', 'Asterias rubens', 'Animalia', 'Echinodermata', 'Asteroidea', 'Forcipulatida', 'Asteriidae', 'Asterias', 'Equinoderma predador comum em águas costeiras.', 'Fundos rochosos e arenosos', 'Nordeste do Atlântico', 'Moluscos bivalves, mexilhões', 'LC', 'Invertebrado', 'Cinco braços, coloração amarelo a roxo');

-- USUÁRIOS (Senhas em BCrypt)
-- USUÁRIOS (criados automaticamente pelo StartupConfig com senhas: admin123 e user123)

-- QUIZ
INSERT INTO quiz (titulo, descricao, ativo) VALUES ('Quiz Básico de Espécies Marinhas', 'Teste seus conhecimentos sobre identificação de espécies marinhas', true);

-- QUESTÕES DO QUIZ (usando IDs fixos assumindo inserção sequencial)
INSERT INTO questoes_quiz (quiz_id, especie_id, pergunta, opcaoA, opcaoB, opcaoC, opcaoD, respostaCorreta, explicacao, ordem) VALUES (1, 1, 'Qual é esta espécie de mamífero marinho?', 'Golfinho-nariz-de-garrafa', 'Baleia-jubarte', 'Orca', 'Foca-comum', 'A', 'O golfinho-nariz-de-garrafa é reconhecido por seu focinho alongado característico.', 1);

INSERT INTO questoes_quiz (quiz_id, especie_id, pergunta, opcaoA, opcaoB, opcaoC, opcaoD, respostaCorreta, explicacao, ordem) VALUES (1, 3, 'Que peixe vive em simbiose com anêmonas-do-mar?', 'Peixe-cirurgião', 'Peixe-palhaço', 'Peixe-espada', 'Peixe-papagaio', 'B', 'O peixe-palhaço é famoso por sua relação simbiótica com anêmonas.', 2);

INSERT INTO questoes_quiz (quiz_id, especie_id, pergunta, opcaoA, opcaoB, opcaoC, opcaoD, respostaCorreta, explicacao, ordem) VALUES (1, 4, 'Qual é o maior peixe do mundo?', 'Tubarão-branco', 'Arraia-manta', 'Tubarão-baleia', 'Atum-azul', 'C', 'O tubarão-baleia pode atingir 18 metros de comprimento.', 3);

INSERT INTO questoes_quiz (quiz_id, especie_id, pergunta, opcaoA, opcaoB, opcaoC, opcaoD, respostaCorreta, explicacao, ordem) VALUES (1, 5, 'Qual tartaruga marinha é herbívora na fase adulta?', 'Tartaruga-de-pente', 'Tartaruga-cabeçuda', 'Tartaruga-verde', 'Tartaruga-de-couro', 'C', 'A tartaruga-verde é única por ser principalmente herbívora quando adulta.', 4);

INSERT INTO questoes_quiz (quiz_id, especie_id, pergunta, opcaoA, opcaoB, opcaoC, opcaoD, respostaCorreta, explicacao, ordem) VALUES (1, 6, 'Qual invertebrado marinho é conhecido por sua alta inteligência?', 'Estrela-do-mar', 'Polvo', 'Água-viva', 'Pepino-do-mar', 'B', 'O polvo é um dos invertebrados mais inteligentes.', 5);

-- AVISTAMENTOS (criados via API após login; removidos do import.sql para evitar FK antes da criação dos usuários)
