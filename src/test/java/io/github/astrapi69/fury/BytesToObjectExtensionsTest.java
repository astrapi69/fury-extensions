/**
 * The MIT License
 *
 * Copyright (C) 2022 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.fury;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import io.github.astrapi69.file.create.FileFactory;
import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.read.ReadFileExtensions;
import io.github.astrapi69.file.search.PathFinder;
import io.github.astrapi69.file.write.StoreFileExtensions;
import io.github.astrapi69.test.object.Employee;
import io.github.astrapi69.test.object.Person;
import io.github.astrapi69.test.object.enumeration.Gender;
import io.github.astrapi69.test.object.factory.TestObjectFactory;

/**
 * Test class for {@link BytesToObjectExtensions} which validates its functionality
 */
class BytesToObjectExtensionsTest
{

	/**
	 * Test method for {@link BytesToObjectExtensions#toObject(byte[], Class...)} which verifies the
	 * deserialization of an Employee object
	 *
	 * @throws IOException
	 *             when an IO error occurs during reading or writing files
	 */
	@Test
	void toObject() throws IOException
	{
		Employee employee = TestObjectFactory.newEmployee();
		byte[] bytes = ObjectToBytesExtensions.toBytes(employee, Employee.class, Person.class,
			Gender.class);
		assertNotNull(bytes);
		File file = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(), "byte-object",
			"employee.bts");
		if (!file.exists())
		{
			FileFactory.newFile(file);
		}
		StoreFileExtensions.toFile(file, bytes);
		byte[] fileToBytearray = ReadFileExtensions.readFileToBytearray(file);
		Employee object = BytesToObjectExtensions.toObject(fileToBytearray, Employee.class,
			Person.class, Gender.class);
		assertNotNull(object);
		assertEquals(employee, object);
		DeleteFileExtensions.deleteFile(file);
	}

	/**
	 * Parameterized test for deserializing various objects from byte arrays using a CSV file
	 *
	 * @param employeeId
	 *            the employee id from the CSV
	 * @param firstName
	 *            the first name from the CSV
	 * @param lastName
	 *            the last name from the CSV
	 * @param gender
	 *            the gender from the CSV
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/employee_data.csv", numLinesToSkip = 1)
	void parameterizedToObject(String employeeId, String firstName, String lastName, String gender)
	{
		Person person = Person.builder().name(firstName).nickname(lastName)
			.gender(Gender.valueOf(gender)).build();
		Employee employee = Employee.builder().id(employeeId).person(person).build();

		byte[] bytes = ObjectToBytesExtensions.toBytes(employee, Employee.class, Person.class,
			Gender.class);
		assertNotNull(bytes);

		Employee deserializedEmployee = BytesToObjectExtensions.toObject(bytes, Employee.class,
			Person.class, Gender.class);
		assertNotNull(deserializedEmployee);
		assertEquals(employee, deserializedEmployee);
	}
}
