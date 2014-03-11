#!/usr/bin/rhino
importPackage(java.io);

load('util.js');
load('parse.js');

model = parse('frames.txt');

model.classes.each(function(clazz) {
	printClass(clazz);
});
	
function printClass(clazz)
{
	print('// size: ', clazz.getSize()>>3);
	if (clazz.parent)
		print('class', clazz.name, 'extends', clazz.parent.name);
	else
		print('class', clazz.name);

	print('{');
	
	for (var i=0, attribute; attribute=clazz.attributes[i++];)
		generateField(attribute);
	print('');
		
	for (var i=0, attribute; attribute=clazz.attributes[i++];)
		printAttribute(attribute);
	
	print('}');
	print('');

}

function printAttribute(attribute)
{
	// Print comments
	print('\t/**');
	if (attribute.comments.length>0)
	{
		for (var i=0; i<attribute.comments.length; i++)
			print('\t *', attribute.comments[i]);
	}
	print('\t */');
	
	generateGetter(attribute);
	print('');
}

function generateField(attribute)
{
	if (attribute.type=='a')
		generateAddressField(attribute);

	if (attribute.type=='s')
		generateByteArrayField(attribute);
}

function generateGetter(attribute)
{
	if (attribute.type=='b')
		if (attribute.size==1)
			generateBitGetter(attribute, 'boolean', 'getBit');
		else
			generateBitGetter(attribute, 'int', 'getBits');

	if (attribute.type=='u')
		if (attribute.size==8)
			generateIntGetter(attribute, 'int', 'getUnsignedByte');
		else if (attribute.size==16)
			generateIntGetter(attribute, 'int', 'getUnsignedShort');
		else if (attribute.size==32)
			generateIntGetter(attribute, 'long', 'getUnsignedInt');
		else if (attribute.size==64)
			generateIntGetter(attribute, 'long', 'getUnsignedLong');
			
	if (attribute.type=='a')
		generateAddressGetter(attribute);
		
	if (attribute.type=='s')
		generateByteArrayGetter(attribute);
}

function generateBitGetter(attribute, returnType, getFunction)
{
	print('\tpublic ' + returnType + ' get' + attribute.name + '()'); 
	print('\t{');
	print('\t\treturn ' + getFunction + '(' + attribute.offset + ',' + attribute.size + ');');
	print('\t}');
}

function generateIntGetter(attribute, returnType, getFunction)
{
	print('\tpublic ' + returnType + ' get' + attribute.name + '()'); 
	print('\t{');
	print('\t\treturn ' + getFunction + '(' + (attribute.offset>>3) + ');');
	print('\t}');
}

function generateByteArrayGetter(attribute)
{
	print('\tpublic ByteBuffer get' + attribute.name + '()'); 
	print('\t{');
	print('\t\treturn ' + fieldName(attribute.name) + ';');
	print('\t}');
}

function generateByteArrayField(attribute)
{
	print('\tprivate ByteBuffer ' + fieldName(attribute.name) + ' = new ByteBuffer(this, ' + (attribute.offset>>3) + ', ' + (attribute.size>>3) + ')'); 
}

function generateAddressGetter(attribute)
{
	print('\tpublic ByteBuffer get' + attribute.name + '()'); 
	print('\t{');
	print('\t\treturn ' + fieldName(attribute.name) + ';');
	print('\t}');
}

function generateAddressField(attribute)
{
	print('\tprivate Address ' + fieldName(attribute.name) + ' = new Address(this, ' + (attribute.offset>>3) + ')'); 
}

function fieldName(name)
{
	return name.substring(0,1).toLowerCase() + name.substring(1);
}

