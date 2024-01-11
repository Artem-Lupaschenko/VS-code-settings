let nums = [40, 11, 83, 21, 75, 64];

let numIndx = 0;

function hoareSorting(pos, arr) {
    for (let i = arr.length - 1; i > 0; i--) {
        if (arr[pos] > arr[i]) {
            let temp = arr[i];
            arr[i] = arr[pos];
            arr[pos] = temp;
            pos = i;
            for (let j = 0; j < pos; j++) {
                if (arr[pos] < arr[j]){
                    let temp = arr[j];
                    arr[j] = arr[pos];
                    arr[pos] = temp;
                    pos = j;
                    break;
                }
            }
            numIndx = pos;
            return arr;
        }
    }
}

nums = hoareSorting(numIndx, nums)

let firstPartOfArr = nums.slice(0, numIndx);
let lastPartOfArr = nums.slice(numIndx, nums.length);

console.log(hoareSorting(0, firstPartOfArr).concat(hoareSorting(numIndx, lastPartOfArr))); 
