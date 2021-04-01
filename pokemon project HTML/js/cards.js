$(document).ready(function () {
    loadCards();
});

function loadCards() {
	// Define variable to hold the html to be updated
    var grid1 = $('#gridContentCol1');
	// Define variable to hold the html to be updated
    var grid2 = $('#gridContentCol2');
	// Define variable to hold the html to be updated
    var grid3 = $('#gridContentCol3');
    // Define variable to hold html to be updated
    var grid4 = $('#gridContentCol4');

	  // Ajax call to API to retrieve the information through 'GET' http method
    $.ajax({
		// Type of http method
        type: 'GET',
		// URL to go to
        url: 'http://tsg-vending.herokuapp.com/items',
		// If the call succeeds, retrieve the array and the indexes
        success: function(itemArray) {
			// For each of the items
            $.each(itemArray, function(index, item){
				// Define variable to hold id
                var id = item.id;
				// Define variable to hold name
                var name = item.name;
				// Define variable to hold price
                var price = item.price;
				// Define variable to hold quantity
                var quantity = item.quantity;
				// Define variable to display the dynamically retrieved information on cards with bootstrap
				// When the card is clicked, use the id inside to link it to the itemToVend text
				// Style the inside of the card with center text-align and a height of 225
                var card = '<div class="card" onclick="selectedItem(' + id + ')" style="height:225px; text-align:center; font-weight:700">';
                	// Set up the card body    
					card += '<div class="card-body">';
					// Set up the alignment of id, override the center-align above
                    card += '<div style="text-align:left">' + id + '</div>';
					// Use a break to give space, place the name, and use another two breaks to give more space
                    card += '<br />' + name + '<br /><br />';
					// Format the price using formatToCurrency function, use break to give space
                    card += '$'+ formatToCurrency(price) + '<br /><br />';
					// Display the quantity left, close off the divs, and add one last break for space
                    card += 'Quantity Left: ' + quantity + '</div></div><br />';
                
					// Use the modulus to assign where the cards go by index
                    if(index % 4 == 0){
					// Add the care to grid1 / column 1
                    grid1.append(card);
                }
				// Use the modulus to assign where the cards go by index
                else if(index % 4 == 1){
					// Add the care to grid2 / column 2
                    grid2.append(card);
                }
				// Use the modulus to assign where the cards go by index
                else if(index % 4 == 2){
					// Add the care to grid3 / column 3
                    grid3.append(card);
                } else if(index % 4 == 2){
					// Add the care to grid3 / column 3
                    grid4.append(card);
                }

            })
        },
		// If there is an error retrieving the information, show this error message
        error: function() {
			// This will be "above" the columns and forms
            $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later.'));
        }
    });
}