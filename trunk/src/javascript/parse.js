function parse(file)
{
	var model = {
		classes: []
	};
	
	var reader = new BufferedReader(new FileReader(file));
	
	var line, attributes = [], offset, comments=[];
	while (line=reader.readLine())
	{
		// Skip % comment lines and empty lines
		if (line.trim()=='' || line.trim().startsWith('%'))
			continue;

		// If the line is a # comment line, collect it and continue
		if (line.trim().indexOf("#")==0)
		{
			comments.push(line.trim().substring(1).trim());
			continue;
		}
		
		// Coerce to JavaScript string			
		line = '' + line;
			
		if (line.indexOf("@")==0)
		{
			// New class
			names = line.substring(1).split(' ');
			var name = names[0];
			var type, subType;

			if (name.indexOf('[')!=-1)
			{
				var parts = name.split('[');
				name = parts[0];
				var types = (parts[1].substring(0, parts[1].length-1)).split(',');
				var type = parseInt(types[0], 10);
				var subType = parseInt(types[1], 10);
				print(name, type, subType);
			}
			
			offset = 0;
			
			model.classes.push({
				name : name,
				parent : names[1],
				attributes : attributes = [],
				getSize : getSize,
				comments : comments,
				type : type,
				subType : subType
			});
			comments = [];
		} else
		{
			// Class attribute
			var parts = line.trim().split(' ');
			var type = parseType(parts[0]);
			var name = parts[1];
			
			attributes.push({
				type: type.type,
				offset : offset,
				size: type.size,
				name: name,
				comments : comments
			});
			
			offset += type.size;
			comments = [];
		}
	}
	
	// Resolve inheritance
	for (var i=0, clazz; clazz=model.classes[i++];)
		if (clazz.parent)
			for (var j=0, parent; parent=model.classes[j++];)
				if (clazz.parent==parent.name)
				{
					clazz.parent = parent;
					
					// shift offsets
					for (var l=0, attribute; attribute=clazz.attributes[l++];)
						attribute.offset += parent.getSize();
					
					break;
				}
	
	return model;
}

function parseType(type)
{
	if (type=='address')
	{
		return {
			type : 'a',
			size : 6*8
		};
	} else
	{
		var ret = { size: 1 };

		ret.type = type[0];
	
		if (type.length>1)
			ret.size = parseInt(type.substring(1), 10);
			
		if (ret.type=='s')
			ret.size <<= 3;
	
		return ret;
	}

}

function getSize()
{
	var size = this.parent ? this.parent.getSize() : 0;
	
	for (var i=0, attribute; attribute=this.attributes[i++];)
		size += attribute.size;
		
	return size;
}

