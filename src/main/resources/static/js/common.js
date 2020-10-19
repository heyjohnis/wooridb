function createSelectOption(obj, id, key, val){
	$.each(obj, function(i, el){
		$("#"+id).append("<option value='"+el[key]+"'>"+el[val]+"</option>");
	});	
}