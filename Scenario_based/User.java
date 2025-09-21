using System;
using System.Collections.Generic;

namespace Artify
{
    // IPurchasable Interface
    public interface IPurchasable
    {
        bool Purchase(User user);
        bool License(User user, string licenseType);
    }

    // User Class
    public class User
    {
        private decimal walletBalance;
        public string Name { get; private set; }
        public string Email { get; private set; }
        public List<Artwork> PurchasedArtworks { get; private set; }

        public User(string name, string email, decimal initialBalance = 0)
        {
            Name = name;
            Email = email;
            walletBalance = initialBalance;
            PurchasedArtworks = new List<Artwork>();
        }

        public decimal GetBalance() => walletBalance;

        public void AddFunds(decimal amount)
        {
            if (amount > 0)
                walletBalance += amount;
        }

        public bool DeductFunds(decimal amount)
        {
            if (walletBalance >= amount)
            {
                walletBalance -= amount;
                return true;
            }
            return false;
        }

        public void AddArtwork(Artwork artwork)
        {
            PurchasedArtworks.Add(artwork);
        }
    }

    // Base Artwork Class
    public abstract class Artwork : IPurchasable
    {
        public string Title { get; protected set; }
        public string Artist { get; protected set; }
        public decimal Price { get; protected set; }
        protected string licenseType;
        public bool HasPreview { get; protected set; }

        protected Artwork(string title, string artist, decimal price, string licenseType, bool hasPreview = false)
        {
            Title = title;
            Artist = artist;
            Price = price;
            this.licenseType = licenseType;
            HasPreview = hasPreview;
        }

        public string GetLicenseTerms()
        {
            return licenseType switch
            {
                "Standard" => "Personal use only",
                "Commercial" => "Commercial use allowed",
                "Exclusive" => "Exclusive rights",
                _ => "Usage terms vary"
            };
        }

        public abstract bool Purchase(User user);
        public abstract bool License(User user, string licenseType);

        public virtual void DisplayInfo()
        {
            Console.WriteLine($"Title: {Title}");
            Console.WriteLine($"Artist: {Artist}");
            Console.WriteLine($"Price: {Price:C}");
            Console.WriteLine($"License: {licenseType}");
        }
    }

    // Derived Artwork Types
    public class DigitalArt : Artwork
    {
        public string FileFormat { get; private set; }
        public string Resolution { get; private set; }

        public DigitalArt(string title, string artist, decimal price, string licenseType, 
            string fileFormat, string resolution, bool hasPreview = false)
            : base(title, artist, price, licenseType, hasPreview)
        {
            FileFormat = fileFormat;
            Resolution = resolution;
        }

        public override bool Purchase(User user)
        {
            if (user.DeductFunds(Price))
            {
                user.AddArtwork(this);
                Console.WriteLine($"Purchased digital art: {Title}");
                return true;
            }
            return false;
        }

        public override bool License(User user, string newLicenseType)
        {
            // Digital art licensing logic
            decimal licenseFee = newLicenseType switch
            {
                "Commercial" => Price * 2,
                "Exclusive" => Price * 5,
                _ => Price
            };

            if (user.DeductFunds(licenseFee))
            {
                licenseType = newLicenseType;
                Console.WriteLine($"Licensed {Title} as {newLicenseType} for {licenseFee:C}");
                return true;
            }
            return false;
        }

        public override void DisplayInfo()
        {
            base.DisplayInfo();
            Console.WriteLine($"Format: {FileFormat}");
            Console.WriteLine($"Resolution: {Resolution}");
        }
    }

    public class PrintArt : Artwork
    {
        public string PrintSize { get; private set; }
        public string Material { get; private set; }

        public PrintArt(string title, string artist, decimal price, string licenseType,
            string printSize, string material, bool hasPreview = false)
            : base(title, artist, price, licenseType, hasPreview)
        {
            PrintSize = printSize;
            Material = material;
        }

        public override bool Purchase(User user)
        {
            // Print art may have shipping costs
            decimal totalCost = Price + 15; // $15 shipping
            if (user.DeductFunds(totalCost))
            {
                user.AddArtwork(this);
                Console.WriteLine($"Purchased print: {Title} (including shipping)");
                return true;
            }
            return false;
        }

        public override bool License(User user, string newLicenseType)
        {
            // Print art licensing is different
            if (newLicenseType == "Exclusive")
            {
                Console.WriteLine("Exclusive licensing not available for physical prints");
                return false;
            }

            decimal licenseFee = newLicenseType == "Commercial" ? Price * 1.5m : Price;
            if (user.DeductFunds(licenseFee))
            {
                licenseType = newLicenseType;
                Console.WriteLine($"Licensed print {Title} as {newLicenseType}");
                return true;
            }
            return false;
        }

        public override void DisplayInfo()
        {
            base.DisplayInfo();
            Console.WriteLine($"Size: {PrintSize}");
            Console.WriteLine($"Material: {Material}");
        }
    }
}