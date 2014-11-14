// Gets the element associated with the id.
function $(id) {
  return document.getElementById(id);
}

// Displays the element associated with the id.
function displayElement(id) {
  $(id).style.display = "inline";
}

// Removes the element associated with the id.
function hideElement(id) {
  $(id).style.display = "none";
}

// Shows the element associated with the id.
function revealElement(id) {
  $(id).style.visibility = "visible";
}

// Hides the element associated with the id.
function concealElement(id) {
  $(id).style.visibility = "hidden";
}

// Changes the element content associated with the id.
function changeElement(id,
                       content) {
  $(id).innerHTML = content;
}
