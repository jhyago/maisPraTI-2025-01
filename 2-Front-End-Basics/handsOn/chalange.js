

// exemple of input and output
// Input: stop
// Output: opstay
// Input: no
// Output: onay
// Input: people
// Output: eoplepay
// Input: bubble
// Output: ubblebay
// Input: under
// Output: underay
// Input: admitted
// Output: admitteday
// Input: away
// Output: awayay



// Descrição Uma língua antiga foi descoberta recentemente, que parece ser uma língua inglesa próxima derivado. Um grupo de pesquisadores requer um programa para traduzir o inglês para este texto antigo. As regras para traduzir qualquer palavra em inglês para esta língua estrangeira estão listadas abaixo. Regras de tradução


// //*1. Separe cada palavra em duas partes. um. A primeira parte é chamada de "prefixo" e se estende desde o início da palavra até, mas não incluindo, a primeira vogal (A letra "y" será considerada uma vogal). b. O resto da palavra é chamado de "tronco".

//  2. O texto traduzido é formado pela troca da ordem do prefixo e da raiz, e adicionando as letras "ay" ao final. um. Por exemplo, "sanduíche" é composto por "s" + "andwich" e se traduz em "andwichsay", que é "andwich" + "s" + "ay".

const words = ["stop", "no", "people", "bubble", "under", "admitted", "away"];


// function translateForOldEnglish(word) {
//     const vogais = ["a", "e", "i", "o", "u", "y"];

//     const prefix = vogais.find(vogal => word.indexOf(vogal) !== -1);

//     const stem = word.split(prefix)

//     console.log(stem[0] + prefix + "ay")
// }

function traduzirPalavra(palavra) {
    const vogais = ['a', 'e', 'i', 'o', 'u', 'y'];
    let indiceDaVogal = -1;

    // --- SEU DESAFIO ESTÁ AQUI ---
    // Crie um loop (for) que passe pelas letras da 'palavra'.
    // Dentro do loop, verifique se a letra atual é uma vogal.
    // Se for, guarde o índice dela na variável 'indiceDaVogal' e pare o loop (break).
    // --- FIM DO DESAFIO ---
    for (let i = 0; i < palavra.length; i++) {
        if (vogais.includes(palavra[i])) {
            indiceDaVogal = i
            break
        }
    }

    // Se a palavra não tem vogal ou começa com vogal, a regra muda um pouco
    if (indiceDaVogal <= 0) {
        return palavra + "ay";
    }

    // Agora, usando o .slice() que você pesquisou
    const prefixo = palavra.slice(0, indiceDaVogal);
    const stem = palavra.slice(indiceDaVogal);

    // Montando o resultado final
    return stem + prefixo + "ay";
}

// Testando
console.log(traduzirPalavra("stop")); // Deveria ser "opstay"
console.log(traduzirPalavra("under")); // Deveria ser "underay"

// translateForOldEnglish('bubble'); // Testando

// Input: people
// Output: eoplepay
// words.forEach((word) => translateForOldEnglish(word))
// translateForOldEnglish(words[3])
