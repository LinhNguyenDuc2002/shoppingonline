const popular = document.querySelector(".popular");
const latest = document.querySelector(".latest");
const bestseller = document.querySelector(".bestseller");

function showSelected(a,b,c){
    a.classList.add('selected');
    if(b.classList.contains('selected')){
        b.classList.remove('selected');
    } 
    if(c.classList.contains('selected')){
        c.classList.remove('selected');
    } 
    // event.preventDefault();
}

popular.addEventListener("click",function(){
    showSelected(popular,latest,bestseller);
});
latest.addEventListener("click",function(){
    showSelected(latest,popular,bestseller);
});
bestseller.addEventListener("click",function(){
    showSelected(bestseller,latest,popular)
});
