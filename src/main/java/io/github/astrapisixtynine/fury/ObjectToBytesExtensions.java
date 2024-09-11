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

import java.util.Arrays;

import org.apache.fury.Fury;
import org.apache.fury.config.Language;

import lombok.NonNull;

/**
 * Utility class for converting Java objects to byte arrays
 */
public final class ObjectToBytesExtensions
{

	/**
	 * Private constructor to prevent instantiation
	 */
	private ObjectToBytesExtensions()
	{
	}

	/**
	 * Converts an object to a byte array
	 *
	 * @param object
	 *            the object to be serialized
	 * @param registerClasses
	 *            classes that need to be registered for serialization
	 * @param <T>
	 *            the type of the object to be serialized
	 * @return the serialized byte array
	 */
	public static <T> byte[] toBytes(final @NonNull T object, final Class<?>... registerClasses)
	{
		Fury fury = Fury.builder().withLanguage(Language.JAVA).build();
		return toBytes(fury, object, registerClasses);
	}

	/**
	 * Converts an object to a byte array using a specific Fury instance
	 *
	 * @param fury
	 *            the Fury instance used for serialization
	 * @param object
	 *            the object to be serialized
	 * @param registerClasses
	 *            classes that need to be registered for serialization
	 * @param <T>
	 *            the type of the object to be serialized
	 * @return the serialized byte array
	 */
	public static <T> byte[] toBytes(final @NonNull Fury fury, final @NonNull T object,
		final Class<?>... registerClasses)
	{
		if (registerClasses != null && registerClasses.length > 0)
		{
			Arrays.stream(registerClasses).forEach(fury::register);
		}
		return fury.serialize(object);
	}
}
