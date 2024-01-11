let nums = [40, 11, 83, 21, 75, 64];

let numIndx = 0;

function hoareSorting(pos, fdsf) {
    for (let i = fdsf.length - 1; i > 0; i--) {
        if (fdsf[pos] > fdsf[i]) {
            let temp = fdsf[i];
            fdsf[i] = fdsf[pos];
            fdsf[pos] = temp;
            pos = i;
            for (let j = 0; j < pos; j++) {
                if (fdsf[pos] < fdsf[j]){
                    let temp = fdsf[j];
                    fdsf[j] = fdsf[pos];
                    fdsf[pos] = temp;
                    pos = j;
                    break;
                }
            }
            numIndx = pos;
            return fdsf;
        }
    }
}

nums = hoareSorting(numIndx, nums)

let firstPartOfArr = nums.slice(0, numIndx);
let lastPartOfArr = nums.slice(numIndx, nums.length);

console.log(hoareSorting(0, firstPartOfArr).concat(hoareSorting(numIndx, lastPartOfArr))); 
