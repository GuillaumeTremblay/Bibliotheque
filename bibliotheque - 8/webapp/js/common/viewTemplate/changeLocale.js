// Changes the user locale
function changeLocale(formID,
		              languageIsoCode,
		              countryIsoCode) {
  $("language-iso-code").value = languageIsoCode;
  $("country-iso-code").value = countryIsoCode;
  $(formID).submit();
}
