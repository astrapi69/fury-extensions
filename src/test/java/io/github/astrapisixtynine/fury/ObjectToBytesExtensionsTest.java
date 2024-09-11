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
package io.github.astrapisixtynine.fury;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import io.github.astrapi69.test.object.Employee;
import io.github.astrapi69.test.object.Person;
import io.github.astrapi69.test.object.enumeration.Gender;
import io.github.astrapi69.test.object.factory.TestObjectFactory;

/**
 * Test class for {@link ObjectToBytesExtensions} which validates its functionality
 */
class ObjectToBytesExtensionsTest
{

	/**
	 * Test method for {@link ObjectToBytesExtensions#toBytes(Object, Class...)} which verifies the
	 * serialization of an Employee object
	 */
	@Test
	void toBytes()
	{
		Employee employee = TestObjectFactory.newEmployee();
		byte[] bytes = ObjectToBytesExtensions.toBytes(employee, Employee.class, Person.class,
			Gender.class);
		assertNotNull(bytes);
		Employee object = BytesToObjectExtensions.toObject(bytes, Employee.class, Person.class,
			Gender.class);
		assertNotNull(object);
		assertEquals(employee, object);
	}

	/**
	 * Parameterized test for serializing various Employee objects from CSV file
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
	void parameterizedToBytes(String employeeId, String firstName, String lastName, String gender)
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
