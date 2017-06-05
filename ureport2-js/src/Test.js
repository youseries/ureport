var LETTERS=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
var NEWLATTERS=LETTERS.concat([]);
for(var i=0;i<LETTERS.length;i++){
    var name=LETTERS[i];
    for(var j=0;j<LETTERS.length;j++){
        NEWLATTERS.push(name+LETTERS[j]);
    }
}
console.log(NEWLATTERS.length);
var countCols=51;
console.log(NEWLATTERS[countCols-1]);