// View the template index page
function viewTemplateIndex(formID) {
  $(formID).action = "viewTemplateIndex.do";
  $(formID).submit();
}
