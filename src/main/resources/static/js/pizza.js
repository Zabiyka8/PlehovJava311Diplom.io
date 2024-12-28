'use strict';


const inputPizzaId = document.getElementById('input_pizzaId');

const addToCartRequest = () => $.ajax({
    url: '/cart/add',
    method: 'POST',
    dataType: 'html',
    data: {
        pizzaId: inputPizzaId.value
    }
});

const button = document.querySelector('button');

button.addEventListener('click', addToCartRequest);



