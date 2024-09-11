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

import java.util.Arrays;

import org.apache.fury.Fury;
import org.apache.fury.config.Language;

import lombok.NonNull;

/**
 * Utility class for converting byte arrays to Java objects
 */
public class BytesToObjectExtensions
{

	/**
	 * Converts a byte array to an object
	 *
	 * @param bytes
	 *            the byte array to deserialize
	 * @param registerClasses
	 *            classes that need to be registered for deserialization
	 * @param <T>
	 *            the type of the deserialized object
	 * @return the deserialized object
	 */
	public static <T> T toObject(final byte[] bytes, Class<?>... registerClasses)
	{
		Fury fury = Fury.builder().withLanguage(Language.JAVA).build();
		return toObject(fury, bytes, registerClasses);
	}

	/**
	 * Converts a byte array to an object using a specific Fury instance
	 *
	 * @param fury
	 *            the Fury instance used for deserialization
	 * @param bytes
	 *            the byte array to deserialize
	 * @param registerClasses
	 *            classes that need to be registered for deserialization
	 * @param <T>
	 *            the type of the deserialized object
	 * @return the deserialized object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toObject(final @NonNull Fury fury, final byte[] bytes,
		final Class<?>... registerClasses)
	{
		if (registerClasses != null && registerClasses.length > 0)
		{
			Arrays.stream(registerClasses).forEach(fury::register);
		}
		return (T)fury.deserialize(bytes);
	}
}
